package Pandatron76_StSMod.patches;

import Pandatron76_StSMod.custom_relics.GremlinsDice;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

@SpirePatch(clz= Defect.class, method="getStartingRelics")
public class getDefectStartingRelicsPatch {

    public static ArrayList<String> Postfix(ArrayList<String> __result, Defect __instance) {
        //Clear out the original relics
        __result.clear();
        //Add the custom relic 'Golden Marble' to the starting relics
        __result.add(GremlinsDice.ID);
        //Remove the 'Golden Marble' from the relic tracker
        UnlockTracker.markRelicAsSeen(GremlinsDice.ID);
        //Remove the 'Burning Blood' from the relic tracker
        UnlockTracker.markRelicAsSeen("Cracked Core");
        //Return the relics that Ironclad will start with
        return __result;
    }
}