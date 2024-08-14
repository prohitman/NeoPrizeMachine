package com.prohitman.prizemachines.blocks;

import com.prohitman.prizemachines.PrizeMachines;
import com.prohitman.prizemachines.registry.ItemRegistry;
import com.prohitman.prizemachines.registry.SoundEventRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class PrizeMachineBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoubleBlockHalf> DOUBLE_BLOCK_HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public static final ResourceLocation EQUIPMENT_DROP = PrizeMachines.MOD_RL.withPath("equipment");
    public static final ResourceLocation BLOCK_DROP = PrizeMachines.MOD_RL.withPath("block");

    public PrizeMachineBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getValue(DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER ? Shapes.empty() : super.getOcclusionShape(pState, pLevel, pPos);
    }

    @Override
    public void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction direction, @Nullable LivingEntity igniter) {
        super.onCaughtFire(state, level, pos, direction, igniter);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack handStack = player.getItemInHand(hand);
        if (!handStack.is(ItemRegistry.TOKENS)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        if (!player.getAbilities().instabuild) {
            handStack.shrink(1);
        }

        player.awardStat(Stats.ITEM_USED.get(handStack.getItem()), 1);
        level.playSeededSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEventRegistry.AWARDED_PRIZE.get(), SoundSource.BLOCKS, 1.0f, 1.0f, 42);

        if (level instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(this);
            ResourceLocation lootTableId = PrizeMachines.MOD_RL.withPath("prizes/" + id.getPath());

            LootTable prizeTable = server.reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, lootTableId));
            LootParams lootParams = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.BLOCK_STATE, state)
                    .withParameter(LootContextParams.ORIGIN, pos.getCenter())
                    .withParameter(LootContextParams.TOOL, handStack)
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .withDynamicDrop(EQUIPMENT_DROP, pOutput -> {
                        Optional<ItemStack> stackOptional;
                        while ((stackOptional = getRandomAllowedItem(serverLevel.enabledFeatures(), serverLevel.getRandomSequence(lootTableId), item -> !(item instanceof BlockItem))).isEmpty());
                        pOutput.accept(stackOptional.get());
                    })
                    .withDynamicDrop(BLOCK_DROP, pOutput -> {
                        Optional<ItemStack> stackOptional;
                        while ((stackOptional = getRandomAllowedItem(serverLevel.enabledFeatures(), serverLevel.getRandomSequence(lootTableId), item -> item instanceof BlockItem)).isEmpty());
                        pOutput.accept(stackOptional.get());
                    })
                    .create(LootContextParamSets.BLOCK);

            prizeTable.getRandomItems(new LootContext.Builder(lootParams).create(Optional.of(lootTableId)), itemStack -> {
                player.sendSystemMessage(Component.translatable("info.prizemachines.awarded", Component.empty().append(itemStack.getDisplayName()).withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(itemStack))))));
                if (!player.getInventory().add(itemStack)) {
                    Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), itemStack);
                }
            });
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);

        BlockPos abovePos = pPos.above();

        if (pState.getValue(DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
            pLevel.setBlock(abovePos, pState.setValue(DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);

        BlockPos toRemove = pState.getValue(DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? pPos.above() : pPos.below();

        if (pLevel.getBlockState(toRemove).is(this)) {
            pLevel.setBlock(toRemove, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos placePos = pContext.replacingClickedOnBlock() ? pContext.getClickedPos() : pContext.getClickedPos().relative(pContext.getHorizontalDirection().getOpposite());

        if (!pContext.getLevel().getBlockState(placePos.above()).canBeReplaced()) {
            return null;
        }
        return super.getStateForPlacement(pContext).setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        if (pMirror == Mirror.FRONT_BACK) {
            return pState.setValue(FACING, pState.getValue(FACING).getOpposite());
        }
        return super.mirror(pState, pMirror);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    protected Optional<ItemStack> getRandomAllowedItem(FeatureFlagSet enabledFlagSet, RandomSource randomSource, Predicate<Item> itemPredicate) {
        return BuiltInRegistries.ITEM.getRandom(randomSource)
                .filter(itemReference -> !itemReference.is(ItemRegistry.PRIZE_BLACKLIST))
                .map(Holder::value)
                .filter(item -> item.isEnabled(enabledFlagSet))
                .filter(itemPredicate)
                .map(ItemStack::new);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if ((isUpperHalf(pState) && !pLevel.getBlockState(pPos.below()).is(this))) {
            return false;
        }
        return super.canSurvive(pState, pLevel, pPos);
    }

    public boolean isLowerHalf(BlockState state) {
        return state.getValue(DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER;
    }

    public boolean isUpperHalf(BlockState state) {
        return state.getValue(DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(FACING, DOUBLE_BLOCK_HALF));
    }
}
