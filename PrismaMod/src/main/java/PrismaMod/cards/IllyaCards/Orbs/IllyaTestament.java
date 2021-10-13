package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.actions.CardActions.Illya.Orbs.TestamentAction;
import PrismaMod.characters.Illya;
import PrismaMod.orbs.IllyaTestamentOrb;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaTestament extends CustomCard {

    public static final String ID = DefaultMod.makeID(IllyaTestament.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/Testament.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public IllyaTestament() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.showEvokeValue = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new TestamentAction(p));
        } else {
            int numMaxOrbs = p.maxOrbs;
            AbstractDungeon.actionManager.addToBottom(new EvokeAllOrbsAction());
            for (int i = 0; i < numMaxOrbs; i++) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaTestamentOrb(), true));
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
}
