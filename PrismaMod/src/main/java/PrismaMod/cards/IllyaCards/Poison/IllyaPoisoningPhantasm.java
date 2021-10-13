package PrismaMod.cards.IllyaCards.Poison;

import PrismaMod.actions.CardActions.Illya.Poison.PoisoningPhantasmAction;
import PrismaMod.characters.Illya;
import PrismaMod.powers.monsters.AbstractMonsterPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.ArrayList;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaPoisoningPhantasm extends CustomCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaPoisoningPhantasm.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/PoisoningPhantasm.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;


    // /STAT DECLARATION/

    public IllyaPoisoningPhantasm() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new PoisoningPhantasmAction(p));
        } else {
            MonsterGroup list = AbstractDungeon.getMonsters();
            holdingCell[] tmpList = new holdingCell[list.monsters.size()];
            int i = 0;
            for (AbstractMonster mon : list.monsters) {
                if (mon.hasPower(PoisonPower.POWER_ID)) {
                    tmpList[i] = new holdingCell(i, mon.getPower(PoisonPower.POWER_ID).amount);
                    //mon.getPower(PoisonPower.POWER_ID).amount = (int)(mon.getPower(PoisonPower.POWER_ID).amount*.75);
                }
                i++;
            }
            int j = 0;
            for (AbstractMonster mon : list.monsters) {
                for (int q = 0; q < tmpList.length; q++) {
                    //if(!(tmpList[q].location == j)) {   //As long as its not the same monster apply the poison stacks
                    try {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mon, mon, new PoisonPower(mon, mon, tmpList[q].numPoison / 4)));
                    } catch (java.lang.NullPointerException e) {
                        DefaultMod.logger.info("There aren't any monsters with poison why would you play this?");
                    }
                    //}
                }
                j++;
            }
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }

    private class holdingCell{
        public int numPoison;
        public int location;
        public holdingCell(int location, int numPoison){
            this.location = location;
            this.numPoison = numPoison;
        }
    }
}
