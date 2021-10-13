package PrismaMod.cards.IllyaCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Misc.GrailAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaGrail extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaGrail.class.getSimpleName());
    public static final String IMG = makeCardPath("Illya/Grail.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    public static final int ENERGYGAIN = 2;
    public static int NUMDRAW = 2;
    public static final int UPGRADED_DRAW = 1;

    public static final int COST = 1;

    public IllyaGrail(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaustOnUseOnce = true;
        this.baseMagicNumber = this.magicNumber = draw = NUMDRAW;
    }
    @Override
    public void upgrade() {

        upgradeName();
        draw += UPGRADED_DRAW;
        upgradeMagicNumber(UPGRADED_DRAW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new GrailAction(p, draw, ENERGYGAIN));
        } else {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.draw));
            p.gainEnergy(ENERGYGAIN);
        }
    }


}
