package PrismaMod.cards.IllyaCards.Poison;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

//Gain block based on total poison on all enemies
public class IllyaUberPoisonBlock extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaUberPoisonBlock.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/UberPoisonBlock.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;
    private static final int UP_COST = 2;
    private static final int BLOCK = 0;
    private static final int UPGRADE_PLUS_BLOCK = 1;
    private boolean magicNumUpdated = false;

    public IllyaUberPoisonBlock(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseBlock = block = BLOCK;
        this.baseMagicNumber = this.magicNumber = BLOCK;
        //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.

    }

    @Override
    public void update() {
        super.update();
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = 0;
        try {
            if (AbstractDungeon.player != null && !AbstractDungeon.getMonsters().monsters.isEmpty()) {
                for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                    if (!this.upgraded) {
                        this.baseMagicNumber = this.magicNumber = this.magicNumber + (mon.hasPower(PoisonPower.POWER_ID) ? mon.getPower(PoisonPower.POWER_ID).amount : 0);
                    } else {
                        this.baseMagicNumber = this.magicNumber = this.magicNumber + (mon.hasPower(PoisonPower.POWER_ID) ? mon.getPower(PoisonPower.POWER_ID).amount * 2 : 0);
                    }
                }
            }
        } catch (java.lang.NullPointerException e) {
            this.baseMagicNumber = this.magicNumber = 0;
        }
        initializeDescription();
    }

    public void upgrade(){
        if (!upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void use(AbstractPlayer player, AbstractMonster monster){
        if(player instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.magicNumber));
        } else {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.magicNumber));

        }
        this.magicNumber = 0;   //Reset just in case
    }
}
