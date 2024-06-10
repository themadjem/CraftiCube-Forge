package com.themadjem.crafticube.block.entity;

import com.themadjem.crafticube.CraftiCube;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrafticubeBlockEntity extends BlockEntity {
    public final int FLUID_LIMIT;
    private final ItemStackHandler itemHandler;
    private LazyOptional<IItemHandlerModifiable> lazyItemHandler = LazyOptional.empty();
//    private NonNullList<ItemStack> items;
    private final FluidTank[] fluidTanks;

    public CrafticubeBlockEntity(BlockPos pPos, BlockState pBlockState, int invSlots, int tankSize) {
        super(BlockEntityType.CHEST, pPos, pBlockState);
        itemHandler = new ItemStackHandler(invSlots);
//        this.items = NonNullList.withSize(invSlots, ItemStack.EMPTY);
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
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    /*
     * This does not drop fluids, I'm not sure if there is a way that I can make it able to.
     * I also am not sure if I want it to drop its contents or keep it, it is expensive to craft, so having it be able to
     * keep its inventory on pickup is feasable
     * */
    public void drops() {
        Containers.dropContents(this.level, this.worldPosition, this.getInventory());
    }

    public SimpleContainer getInventory(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return inventory;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        CraftiCube.logInfo(itemHandler.serializeNBT().toString());
        for (int i = 0; i < fluidTanks.length; i++) {
            pTag.put("fluid" + i, fluidTanks[i].writeToNBT(new CompoundTag()));
            CraftiCube.logInfo(fluidTanks[i].writeToNBT(new CompoundTag()).toString());
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        for (int i = 0; i < fluidTanks.length; i++) {
            fluidTanks[i].readFromNBT(pTag.getCompound("fluid" + i));
        }
    }
}
