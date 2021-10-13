package PrismaMod.cards.MiyuCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Chloe.AddCardFromGridSelectAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Miyu;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuChristmas extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(MiyuChristmas.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/Christmas.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;

    private static final int COST = 1;
    private final int NUM_CARDS = 1;
    private final int NUM_CARDS_UP = 1;


    // /STAT DECLARATION/

    public MiyuChristmas() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = NUM_CARDS;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToTop(new AddCardFromGridSelectAction(p, p.drawPile, this.magicNumber, ""));

    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            //rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeMagicNumber(NUM_CARDS_UP);
        }
    }
}
