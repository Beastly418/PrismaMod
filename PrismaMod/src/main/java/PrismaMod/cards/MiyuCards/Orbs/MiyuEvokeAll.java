package PrismaMod.cards.MiyuCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Miyu;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuEvokeAll extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(MiyuEvokeAll.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/EvokeAll.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;
    private static final int COST = 0;
    private static final int UP_COST = 0;


    // /STAT DECLARATION/

    public MiyuEvokeAll() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.showEvokeValue = true;
        this.baseMagicNumber = this.magicNumber = 1;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int numOrbs = p.orbs.size();
        for(AbstractOrb o : p.orbs) {
            if(o.ID != null) {
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.magicNumber));
                AbstractDungeon.actionManager.addToTop(new EvokeOrbAction(1));
            }
        }
        //AbstractDungeon.actionManager.addToTop(new DrawCardAction(numOrbs * this.magicNumber));
        //AbstractDungeon.actionManager.addToTop(new EvokeAllOrbsAction());
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            initializeDescription();
            this.upgradeMagicNumber(1);
            //upgradeBaseCost(UP_COST);
        }
    }
}
