package PrismaMod.cards.IllyaCards.Misc;

import PrismaMod.actions.CardActions.Illya.Misc.CommandSealAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;


import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaCommandSeal extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaCommandSeal.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/CommandSeal.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static int COST = 1;
    //private static int NUMUSES = 3;
    private static int HEAL = 3;
    private static int HEAL_UP = 2;
    public int numUses = 3;
    private int heal;

    // /STAT DECLARATION/

    public IllyaCommandSeal(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.defaultBaseSecondMagicNumber = this.defaultSecondMagicNumber = 1;
        this.heal = this.baseMagicNumber = this.magicNumber = HEAL;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new CommandSealAction(p, this.heal));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1)));
            AbstractDungeon.actionManager.addToTop(new HealAction(p, p, this.heal));
        }
        //AbstractDungeon.actionManager.addToBottom(new IllyaModifyNumUsesAction(this.uuid, -1)); //Booooooooooooooooooooooooooooooo

    }

    @Override
    public AbstractCard makeCopy() {
        return new IllyaCommandSeal();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeHeal(HEAL_UP);
            initializeDescription();
        }
    }

    public void upgradeHeal(int amt){
        this.heal = this.heal + amt;
        upgradeMagicNumber(amt);
    }
}
