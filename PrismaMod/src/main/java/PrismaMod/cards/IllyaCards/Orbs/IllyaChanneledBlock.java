package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Orbs.ChanneledBlockAction;
import PrismaMod.actions.CardActions.Illya.Orbs.RapidFireAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

//Gain block based on number of orbs you have channeled
@Deprecated
public class IllyaChanneledBlock extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaChanneledBlock.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/MultiEvoke.png");

    public static final String NAME = cardStrings.NAME;
    public static String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int NUM_ATTACKS = 0;
    private static final int BLOCK = 3;
    private static final int UP_BLOCK = 1;

    public IllyaChanneledBlock() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = NUM_ATTACKS;
        this.baseBlock = this.baseDamage = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new ChanneledBlockAction(p, this.block));
        } else {
            for(int i = 0; i < this.magicNumber; i++) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.magicNumber));
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            upgradeBlock(UP_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public void update() {
        super.update();
        if(AbstractDungeon.player != null)
            this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.orbs.size();
    }
}