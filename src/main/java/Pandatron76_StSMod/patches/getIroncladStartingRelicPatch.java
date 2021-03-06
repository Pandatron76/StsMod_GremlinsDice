package Pandatron76_StSMod.patches;

import Pandatron76_StSMod.custom_relics.GremlinsDice;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

@SpirePatch(clz= Ironclad.class, method="getStartingRelics")
public class getIroncladStartingRelicPatch {

    public static ArrayList<String> Postfix(ArrayList<String> __result, Ironclad __instance) {
        // Remove Burning Blood from the starting relics
        __result.remove("Burning Blood");

        // Add the custom relic 'Gremlin's Dice' to the starting relics
        __result.add(GremlinsDice.ID);
        // Remove the 'Gremlin's Dice' from the relic tracker
        UnlockTracker.markRelicAsSeen(GremlinsDice.ID);

        // Remove the 'Burning Blood' from the relic tracker
        UnlockTracker.markRelicAsSeen("Burning Blood");
        // Return the relics that Ironclad will start with
        return __result;
    }
}
