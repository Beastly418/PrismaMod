package PrismaMod.cards.IllyaCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Normal.CommonDefendAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaCommonDefend extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaCommonDefend.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/Defend.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    public IllyaCommonDefend(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseBlock = block =  BLOCK;

        this.tags.add(BaseModCardTags.BASIC_DEFEND); //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.

    }

    public void upgrade(){
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

    public void use(AbstractPlayer player, AbstractMonster monster){
        if(player instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new CommonDefendAction(player, block));
        } else {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, block));
        }
    }
}
