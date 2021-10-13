package PrismaMod.cards.IllyaCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Normal.CarnivalDefendAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import PrismaMod.orbs.IllyaDefendOrb;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaCarnivalDefend extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaCarnivalDefend.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/CarnivalDefend.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 5;
    private int numOrbs = 1;

    public IllyaCarnivalDefend(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseBlock = block = BLOCK;
        this.baseMagicNumber = this.magicNumber = 1;
        //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.

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
            AbstractDungeon.actionManager.addToTop(new CarnivalDefendAction(player, block, numOrbs));
        } else {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, block));
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaDefendOrb(), true));
        }
    }
}
