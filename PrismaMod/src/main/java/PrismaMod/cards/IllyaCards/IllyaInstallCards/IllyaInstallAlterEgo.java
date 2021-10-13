package PrismaMod.cards.IllyaCards.IllyaInstallCards;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Install.InstallAlterEgoAction;
import PrismaMod.characters.Illya;
import PrismaMod.patches.InstallColorEnum;
import PrismaMod.powers.Illya.IllyaAlterEgoPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaInstallAlterEgo extends AbstractInstallCard {

    public static final String ID = DefaultMod.makeID(IllyaInstallAlterEgo.class.getSimpleName());
    public static final String IMG = makeCardPath("Illya/InstallAlterEgo.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = InstallColorEnum.COLOR_INSTALL;

    private static final int COST = 1;
    private static int NUM_ORBS_INC;


    public static IllyaInstallObj INSTALL = new IllyaInstallObj("AlterEgo");

    public IllyaInstallAlterEgo(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, INSTALL);
        NUM_ORBS_INC = this.install.getNumOrbsInc();

        //this.setBackgroundTexture(IMG, IMG);

    }

    @Override
    public IllyaInstallObj getInstall() {
        return install;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public AbstractCard makeCopy() {
        return new IllyaInstallAlterEgo();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Add all of the effects and stuff here!!!
        removeOtherInstall(p);
        install.makeAlterEgo();
        if(p instanceof Illya && !enablePlaceholder)
        AbstractDungeon.actionManager.addToTop(new InstallAlterEgoAction(p));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IllyaAlterEgoPower(p, p, 1, install)));//, 1, AbstractGameAction.AttackEffect.NONE));
        //AbstractDungeon.actionManager.addToTop(new IncreaseMaxOrbAction(NUM_ORBS_INC));
    }
}
