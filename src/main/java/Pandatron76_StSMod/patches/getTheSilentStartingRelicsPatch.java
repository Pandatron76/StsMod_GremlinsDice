package Pandatron76_StSMod.patches;

import Pandatron76_StSMod.custom_relics.GremlinsDice;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

@SpirePatch(clz= TheSilent.class, method="getStartingRelics")
public class getTheSilentStartingRelicsPatch {

    public static ArrayList<String> Postfix(ArrayList<String> __result, TheSilent __instance) {
        // Remove Ring of the Snake from the starting relics
        __result.remove("Ring of the Snake");
        // Add the custom relic 'Gremlin's Dice' to the starting relics
        __result.add(GremlinsDice.ID);
        // Remove the 'Gremlin's Dice' from the relic tracker
        UnlockTracker.markRelicAsSeen(GremlinsDice.ID);

        // Remove the 'Ring of the Snake' from the relic tracker
        UnlockTracker.markRelicAsSeen("Ring of the Snake");
        // Return the relics that TheSilent will start with
        return __result;
    }
}
