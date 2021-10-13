package PrismaMod.cards.IllyaCards.IllyaInstallCards;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Install.InstallRulerAction;
import PrismaMod.characters.Illya;
import PrismaMod.patches.InstallColorEnum;
import PrismaMod.powers.Illya.IllyaRiderPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaInstallRider extends AbstractInstallCard {

    public static final String ID = DefaultMod.makeID(IllyaInstallRider.class.getSimpleName());
    public static final String IMG = makeCardPath("Illya/InstallRider.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = InstallColorEnum.COLOR_INSTALL;

    private static final int COST = 1;

    public static final IllyaInstallObj INSTALL = new IllyaInstallObj("Rider");

    public IllyaInstallRider(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, INSTALL);

        this.baseMagicNumber = this.magicNumber = this.install.getNumDex();
        this.defaultBaseSecondMagicNumber = this.defaultSecondMagicNumber = this.install.getMoreDamagetononBoss();
    }

    @Override
    public IllyaInstallObj getInstall() {
        return install;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Add all of the effects and stuff here!!!
        removeOtherInstall(p);
        install.makeRider();
        if(p instanceof Illya && !enablePlaceholder)
        AbstractDungeon.actionManager.addToTop(new InstallRulerAction(p));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IllyaRiderPower(p, p, 1, install)));//, 1, AbstractGameAction.AttackEffect.NONE));
    }
}
