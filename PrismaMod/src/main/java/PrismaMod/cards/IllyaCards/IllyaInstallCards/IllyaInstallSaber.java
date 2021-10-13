package PrismaMod.cards.IllyaCards.IllyaInstallCards;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Install.InstallSaberAction;
import PrismaMod.characters.Illya;
import PrismaMod.patches.InstallColorEnum;
import PrismaMod.powers.Illya.IllyaSaberPower;
import PrismaMod.powers.SaberDamageIncreasePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaInstallSaber extends AbstractInstallCard {

    public static final String ID = DefaultMod.makeID(IllyaInstallSaber.class.getSimpleName());
    public static final String IMG = makeCardPath("Illya/InstallSaber.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = InstallColorEnum.COLOR_INSTALL;

    private static final int COST = 1;

    public static final IllyaInstallObj INSTALL = new IllyaInstallObj("Saber");

    public IllyaInstallSaber(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, INSTALL);

        this.baseMagicNumber = this.magicNumber = install.getDealMoreDamageToBosses();
        this.defaultBaseSecondMagicNumber = this.defaultSecondMagicNumber = 3;// install.getHealPerTurn();
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
        AbstractDungeon.actionManager.addToTop(new InstallSaberAction(p));
        AbstractDungeon.actionManager.addToTop(new HealAction(p, p, 3));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IllyaSaberPower(p, p, 1, install)));//, 1, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SaberDamageIncreasePower(p, p, 1)));//, 1, AbstractGameAction.AttackEffect.NONE));
    }
}
