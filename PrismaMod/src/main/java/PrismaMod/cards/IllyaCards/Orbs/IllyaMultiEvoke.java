package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.actions.CardActions.Illya.Orbs.MultiEvokeAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import PrismaMod.patches.IllyaCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;

import static PrismaMod.DefaultMod.makeCardPath;
@Deprecated
public class IllyaMultiEvoke extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaMultiEvoke.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/MultiEvoke.png");

    public static final String NAME = cardStrings.NAME;
    public static String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = -1;

    public IllyaMultiEvoke() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(IllyaCardTags.EVOKE_CARD);
        this.showEvokeValue = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom( new MultiEvokeAction(p, this.freeToPlayOnce, this.energyOnUse, this.upgraded));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
