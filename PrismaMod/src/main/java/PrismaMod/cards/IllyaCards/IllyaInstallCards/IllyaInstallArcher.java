package PrismaMod.cards.IllyaCards.IllyaInstallCards;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Install.InstallArcherAction;
import PrismaMod.characters.Illya;
import PrismaMod.patches.InstallColorEnum;
import PrismaMod.powers.Illya.IllyaArcherPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaInstallArcher extends AbstractInstallCard {
    public static final String ID = DefaultMod.makeID(IllyaInstallArcher.class.getSimpleName());
    public static final String IMG = makeCardPath("Illya/InstallArcher.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = InstallColorEnum.COLOR_INSTALL;

    private static final int COST = 1;


    public static IllyaInstallObj INSTALL = new IllyaInstallObj("Archer");

    public IllyaInstallArcher(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, INSTALL);
        this.magicNumber = install.getStrengthInc();
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
        return new IllyaInstallArcher();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Add all of the effects and stuff here!!!
        removeOtherInstall(p);
        install.makeArcher();
        if(p instanceof Illya && !enablePlaceholder)
        AbstractDungeon.actionManager.addToTop(new InstallArcherAction(p));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IllyaArcherPower(p, p, 1, install)));//, 1, AbstractGameAction.AttackEffect.NONE));
        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 5)));
    }
}
