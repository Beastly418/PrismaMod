package PrismaMod.cards.IllyaCards.IllyaInstallCards;


import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Install.InstallForeignerAction;
import PrismaMod.characters.Illya;
import PrismaMod.patches.InstallColorEnum;
import PrismaMod.powers.Illya.IllyaForeignerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaInstallForeigner extends AbstractInstallCard {

    public static final String ID = DefaultMod.makeID(IllyaInstallForeigner.class.getSimpleName());
    public static final String IMG = makeCardPath("Illya/InstallForeigner.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = InstallColorEnum.COLOR_INSTALL;

    private static final int COST = 1;

    public static final IllyaInstallObj INSTALL = new IllyaInstallObj("Foreigner");

    public IllyaInstallForeigner(){
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
        install.makeForeigner();
        if(p instanceof Illya && !enablePlaceholder)
        AbstractDungeon.actionManager.addToTop(new InstallForeignerAction(p));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IllyaForeignerPower(p, p, 1, install)));//, 1, AbstractGameAction.AttackEffect.NONE));
        //AbstractDungeon.actionManager.addToTop(new IncreaseMaxOrbAction(1));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, 1)));
    }
}

