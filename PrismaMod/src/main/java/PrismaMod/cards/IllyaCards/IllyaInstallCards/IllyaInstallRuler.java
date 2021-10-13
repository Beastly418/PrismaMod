package PrismaMod.cards.IllyaCards.IllyaInstallCards;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Install.InstallRulerAction;
import PrismaMod.characters.Illya;
import PrismaMod.patches.InstallColorEnum;
import PrismaMod.powers.Illya.IllyaRulerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaInstallRuler extends AbstractInstallCard {

    public static final String ID = DefaultMod.makeID(IllyaInstallRuler.class.getSimpleName());
    public static final String IMG = makeCardPath("Illya/InstallRuler.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = InstallColorEnum.COLOR_INSTALL;

    private static final int COST = 1;

    public static final IllyaInstallObj INSTALL = new IllyaInstallObj("Ruler");

    public IllyaInstallRuler(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, INSTALL);
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
        install.makeSaber();
        if(p instanceof Illya && !enablePlaceholder)
        AbstractDungeon.actionManager.addToTop(new InstallRulerAction(p));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IllyaRulerPower(p, p, 1, install)));//, 1, AbstractGameAction.AttackEffect.NONE));
    }
}
