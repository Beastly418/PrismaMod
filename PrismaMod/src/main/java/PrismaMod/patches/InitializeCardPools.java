package PrismaMod.patches;

import PrismaMod.characters.Illya;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Map;
import PrismaMod.characters.Illya;
@SpirePatch(clz=com.megacrit.cardcrawl.dungeons.AbstractDungeon.class, method="initializeCardPools")
public class InitializeCardPools {

    @SpireInsertPatch(rloc=26, localvars={"tmpPool"})
    public static void Insert(Object __obj_instance, Object tmpPoolObj) {
        @SuppressWarnings("unchecked")
        ArrayList<AbstractCard> tmpPool = (ArrayList<AbstractCard>) tmpPoolObj;
        if (AbstractDungeon.player.chosenClass == Illya.Enums.ILLYA) {
            AbstractCard card = null;
            for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                card = c.getValue();
                if ((card.color != Illya.Enums.COLOR_ILLYA_PINK && card.color != InstallColorEnum.COLOR_INSTALL)
                        || card.rarity == AbstractCard.CardRarity.BASIC || UnlockTracker.isCardLocked(c.getKey()) && !Settings.isDailyRun) continue;
                tmpPool.add(card);
            }
        }
    }

}