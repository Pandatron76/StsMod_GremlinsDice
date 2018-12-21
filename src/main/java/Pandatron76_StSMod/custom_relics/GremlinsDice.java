package Pandatron76_StSMod.custom_relics;


import basemod.abstracts.CustomRelic;

import com.badlogic.gdx.graphics.Texture;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GremlinsDice extends CustomRelic {

    public static final String ID = "GremlinsDice";
    public static final String IMG = "img/custom_relics/GremlinsDice.png";
    public static final String IMG_OUTLINE = "img/custom_relics/outline/GremlinsDice.png";
    public static final int MAX_HP_SHIFT = 5;
    public static final int GOLD_SHIFT = 15;

    public GremlinsDice(){
        super(ID, new Texture(IMG), new Texture(IMG_OUTLINE),RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void onVictory(){
        flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractPlayer p = AbstractDungeon.player;
        Random rand = new Random();
        int rolld6 = rand.nextInt(6) + 1;

        // On a 1, increase the players max hp by 5 using the same visual cue as the red card 'Feed'
        if (rolld6 == 1) {
            if (p.currentHealth > 0) {
                p.increaseMaxHp(MAX_HP_SHIFT, true);
            }
        }
        // On a 2, decrease the players max hp by 5
        else if (rolld6 == 2) {
            if (p.currentHealth > 0) {
                p.decreaseMaxHealth(MAX_HP_SHIFT);
                AbstractDungeon.effectsQueue.add(
                        new TextAboveCreatureEffect(p.hb.cX - p.animX, p.hb.cY, "Max HP-" +
                                Integer.toString(MAX_HP_SHIFT), Settings.RED_TEXT_COLOR));
                p.healthBarUpdatedEvent();
            }
        }

        //On a 3, provide the player with 15, gold
        else if (rolld6 == 3) {
            p.gainGold(GOLD_SHIFT);
            AbstractDungeon.effectList.add(new RainingGoldEffect(GOLD_SHIFT));

            // Provide a visual indication to let the player know they are gaining gold.
            AbstractDungeon.effectsQueue.add(
                    new TextAboveCreatureEffect(p.hb.cX - p.animX, p.hb.cY, "Gold+" +
                            Integer.toString(GOLD_SHIFT), Settings.GREEN_TEXT_COLOR));
        }

        //On a 4, take 15 gold away from the player
        else if (rolld6 == 4) {
            if (p.gold > 0) {
                p.loseGold(GOLD_SHIFT);

                // Provide a visual indication to let the player know they are gaining gold.
                AbstractDungeon.effectsQueue.add(
                        new TextAboveCreatureEffect(p.hb.cX - p.animX, p.hb.cY, "Gold-" +
                                Integer.toString(GOLD_SHIFT), Settings.RED_TEXT_COLOR));
            }
        }

        // On a 5 give the player a randomly upgraded card
        else if (rolld6 == 5) {

            ArrayList<AbstractCard> upgradableCards = new ArrayList();
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                if (card.canUpgrade()) {
                    upgradableCards.add(card);
                }
            }
            Object cardMetrics = new ArrayList();

            Collections.shuffle(upgradableCards, new java.util.Random(AbstractDungeon.miscRng.randomLong()));
            if (!upgradableCards.isEmpty()) {
                if (upgradableCards.size() == 1)
                {
                    ((AbstractCard)upgradableCards.get(0)).upgrade();
                    ((List)cardMetrics).add(((AbstractCard)upgradableCards.get(0)).cardID);
                    AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
                    AbstractDungeon.effectList.add(
                            new ShowCardBrieflyEffect(
                                    ((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy()));
                }
                else
                {
                    ((AbstractCard)upgradableCards.get(0)).upgrade();
                    ((List)cardMetrics).add(((AbstractCard)upgradableCards.get(0)).cardID);
                    AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));

                    // Position the upgrade card effect in the upper right hand corner of the screen
                    AbstractDungeon.effectList.add(
                            new ShowCardBrieflyEffect(
                                    ((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy(),
                                    Settings.WIDTH / 2.0F + 650.0F * Settings.scale,
                                    Settings.HEIGHT / 2.0F + 150.0F * Settings.scale));
                }
            }
        }

        // On a 6 (or any other condition) give the player a random curse
        else {
            AbstractCard curse = AbstractDungeon.returnRandomCurse();

            // Position the upgrade card effect in the upper left hand corner of the screen
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse,
                    Settings.WIDTH / 2.0F + 650.0F * Settings.scale,
                    Settings.HEIGHT / 2.0F + 150.0F * Settings.scale));
        }
    }

    @Override
    public String getUpdatedDescription(){
        return DESCRIPTIONS[0]
                + DESCRIPTIONS[1]
                + DESCRIPTIONS[2]
                + DESCRIPTIONS[3]
                + DESCRIPTIONS[4]
                + DESCRIPTIONS[5]
                + DESCRIPTIONS[6];
    }

    @Override
    public AbstractRelic makeCopy(){
        return new GremlinsDice();
    }
}
