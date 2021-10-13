package PrismaMod.cards.ChloeCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Chloe.AddCardFromGridSelectAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.cards.ChloeCards.AbstractTraceCard;
import PrismaMod.cards.ChloeCards.Special.*;
import PrismaMod.characters.Chloe;
import PrismaMod.relics.PrismaModTraceOnRelic;
import PrismaMod.relics.PrismaModTraceOnUpgradeRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;


import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeUnlimitedBladeWorks extends AbstractTraceCard {

    public static final String ID = DefaultMod.makeID(ChloeUnlimitedBladeWorks.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/UnlimitedBladeWorks.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private ArrayList<AbstractCard> cards = new ArrayList<>(10);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 3;


    // /STAT DECLARATION/

    public ChloeUnlimitedBladeWorks() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }
    //Choose 3 cards that you've traced before
    //Upgrade Choose 3 cards that you've traced before but they are upgraded
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard tmp;
        if (p.hasRelic(PrismaModTraceOnRelic.ID) || p.hasRelic(PrismaModTraceOnUpgradeRelic.ID)) {

            if(p.hasRelic(PrismaModTraceOnRelic.ID)) {
                PrismaModTraceOnRelic r = (PrismaModTraceOnRelic)p.getRelic(PrismaModTraceOnRelic.ID);
                if(r.traceCards.size() > 0) {
                    if (!upgraded) {
                        for (AbstractCard c : r.traceCards) {
                            tmp = c.makeCopy();
                            tmp.costForTurn = 0;
                            tmp.cost = 0;
                            this.cards.add(tmp);
                        }
                    } else {
                        for (AbstractCard c : r.traceCards) {
                            tmp = c.makeCopy();
                            tmp.upgrade();
                            tmp.costForTurn = 0;
                            tmp.cost = 0;
                            this.cards.add(tmp);
                        }
                    }
                } else {
                    this.defaultCards();
                }
            } else {//if (p.hasRelic(PrismaModTraceOnUpgradeRelic.ID)) {
                PrismaModTraceOnUpgradeRelic r = (PrismaModTraceOnUpgradeRelic)p.getRelic(PrismaModTraceOnUpgradeRelic.ID);
                if(r.traceCards.size() > 0) {
                    if (!upgraded) {
                        for (AbstractCard c : r.traceCards) {
                            tmp = c.makeCopy();
                            tmp.costForTurn = 0;
                            tmp.cost = 0;
                            this.cards.add(tmp);
                        }
                    } else {
                        for (AbstractCard c : r.traceCards) {
                            tmp = c.makeCopy();
                            tmp.upgrade();
                            tmp.costForTurn = 0;
                            tmp.cost = 0;
                            this.cards.add(tmp);
                        }
                    }
                } else {
                    this.defaultCards();
                }
            }

        } else {
            this.defaultCards();
            if(!upgraded){
                for(AbstractCard c : this.cards) {
                    c.costForTurn = 0;
                }
            } else {
                for(AbstractCard c : this.cards) {
                    c.upgrade();
                    c.costForTurn = 0;
                }
            }
        }
        if(!upgraded){
            for(AbstractCard c : this.cards) {
                c.costForTurn = 0;
            }
        } else {
            for(AbstractCard c : this.cards) {
                c.upgrade();
                c.costForTurn = 0;
            }
        }
        AbstractDungeon.actionManager.addToTop(new AddCardFromGridSelectAction(p, this.cards, 3, ""));
        this.cards.clear();
    }

    private void defaultCards(){
        this.cards.add(new ChloeCaladbolg());
        this.cards.add(new ChloeExcalibur());
        this.cards.add(new ChloeEyeOfTheEuryale());
        this.cards.add(new ChloeGaeBolg());
        //this.cards.add(new ChloePhantasmPunishment());
        this.cards.add(new ChloeRhongomyniad());
        this.cards.add(new ChloeRuleBreaker());
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
