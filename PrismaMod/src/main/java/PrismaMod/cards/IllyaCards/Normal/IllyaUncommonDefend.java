package PrismaMod.cards.IllyaCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Normal.UncommonDefendAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaUncommonDefend extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaUncommonDefend.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/UncommonDefend.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;
    private static final int BLOCK = 11;
    private static final int UPGRADE_PLUS_BLOCK = 5;
    private static final int NUMPLATE = 2;
    private static final int UPGRADE_COST = 1;

    public IllyaUncommonDefend(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        block = baseBlock = BLOCK;
        this.magicNumber = this.baseMagicNumber = NUMPLATE;

    }

    public void upgrade(){
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }

    public void use(AbstractPlayer player, AbstractMonster monster){
        if(player instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new UncommonDefendAction(player, block, NUMPLATE));
        } else {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(player, player, block));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new PlatedArmorPower(player, NUMPLATE)));
        }
    }
}
