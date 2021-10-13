package PrismaMod.cards.MiyuCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Orbs.MultiEvokeAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.cards.IllyaCards.Orbs.IllyaMultiEvoke;
import PrismaMod.characters.Illya;
import PrismaMod.characters.Miyu;
import PrismaMod.patches.IllyaCardTags;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuMultiEvoke extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(MiyuMultiEvoke.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/MultiEvoke.png");

    public static final String NAME = cardStrings.NAME;
    public static String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;

    private static final int COST = -1;

    public MiyuMultiEvoke() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.showEvokeValue = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int num = EnergyPanel.totalCount;
        AbstractDungeon.actionManager.addToBottom( new MultiEvokeAction(p, this.freeToPlayOnce, this.energyOnUse, this.upgraded));
        if(this.upgraded){
            for(int i = 0; i < num; i++) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
            }
        }
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
