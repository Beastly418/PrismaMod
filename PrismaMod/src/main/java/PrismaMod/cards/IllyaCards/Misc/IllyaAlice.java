package PrismaMod.cards.IllyaCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Misc.AliceAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import PrismaMod.relics.PrismaModAliceRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaAlice extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaAlice.class.getSimpleName());
    public static final String IMG = makeCardPath("Illya/Alice.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;


    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final int HEAL = 5;
    public static final int NUM_VUL = 2;
    public static final int COST = 1;
    public static final int UPGRADED_COST = 0;

    public IllyaAlice(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(PrismaModAliceRelic.ID)){
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
        exhaustOnUseOnce = true;
        this.defaultBaseSecondMagicNumber = this.defaultSecondMagicNumber = HEAL;
        this.baseMagicNumber = this.magicNumber = NUM_VUL;
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Give player 3 vulnerable
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new VulnerablePower(p, NUM_VUL, true),1, AbstractGameAction.AttackEffect.NONE));
        int rand = (int) (Math.random()*1000);
        if (rand <= 100 && !p.hasRelic(PrismaModAliceRelic.ID)) { //1/10 chance to get the relic and also check if the player has the alice relic
            //Give the player an alice relic that grants you extra card draw

            //AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new PrismaModAliceRelic());
            //rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            //initializeDescription();
            if(p instanceof Illya && !enablePlaceholder) {
                AbstractDungeon.actionManager.addToTop(new AliceAction(p, true, HEAL));
            } else {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new PrismaModAliceRelic());
            }
        } else {
            //Give the player 5 hp back
            if(p instanceof Illya && !enablePlaceholder) {
                AbstractDungeon.actionManager.addToTop(new AliceAction(p, false, HEAL));
            } else {
                AbstractDungeon.actionManager.addToTop(new HealAction(p, p, HEAL));
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(PrismaModAliceRelic.ID)){
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
