package club.someoneice.bee.asm;

import net.tclproject.mysteriumlib.PlaceholderCoremod;

public class PatchCore extends PlaceholderCoremod {
    @Override
    public void registerFixes() {
        registerClassWithFixes(TilePatch.class.getName());
    }
}