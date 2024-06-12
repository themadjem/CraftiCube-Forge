package com.themadjem.crafticube.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrafticubeBlockEntity extends BlockEntity implements Nameable{
    public final int FLUID_LIMIT;
    private final ItemStackHandler itemHandler;
    private LazyOptional<IItemHandlerModifiable> lazyItemHandler;

    private final FluidTank[] fluidTanks;

    public CrafticubeBlockEntity(BlockEntityType<? extends CrafticubeBlockEntity> blockEntityType, BlockPos pPos, BlockState pBlockState, int invSlots, int tankSize) {
        super(blockEntityType, pPos, pBlockState);
        itemHandler = new ItemStackHandler(invSlots);
        lazyItemHandler = LazyOptional.of(() -> this.itemHandler);
        FLUID_LIMIT = tankSize;
        fluidTanks = new FluidTank[]{
                new FluidTank(FLUID_LIMIT),
                new FluidTank(FLUID_LIMIT),
                new FluidTank(FLUID_LIMIT),
                new FluidTank(FLUID_LIMIT)
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER)
            return lazyItemHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("inv")) {
            itemHandler.deserializeNBT(tag.getCompound("inv"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inv", itemHandler.serializeNBT());
    }

    /*
     * This does not drop fluids.
     *
     * I also am not sure if I want it to drop its contents or keep it,
     * it is expensive to craft, so having it be able to
     * keep its inventory on pickup is feasable
     * */
    public void dropContents() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void printContents(Level pLevel, Player pPlayer) {
        if (!pLevel.isClientSide) {
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                ItemStack stack = itemHandler.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    pPlayer.sendSystemMessage(Component.literal("Slot " + i + ": " + stack.getCount() + "x " + stack.getHoverName().getString()));
                }
            }
            for (int i = 0; i < fluidTanks.length; i++) {
                if(!fluidTanks[i].isEmpty()){
                    pPlayer.sendSystemMessage(Component.literal("Tank " + i + ": " + fluidTanks[i].getFluidAmount() + "mB " + fluidTanks[i].getFluid().getDisplayName().getString()));
                }
            }
        }
    }

    @Override
    public Component getName() {
        return null;
    }
}
