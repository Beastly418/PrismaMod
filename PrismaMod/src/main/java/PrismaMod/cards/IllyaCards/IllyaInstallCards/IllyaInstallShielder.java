package PrismaMod.cards.IllyaCards.IllyaInstallCards;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Install.InstallShielderAction;
import PrismaMod.characters.Illya;
import PrismaMod.patches.InstallColorEnum;
import PrismaMod.powers.Illya.IllyaShielderPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaInstallShielder extends AbstractInstallCard {

    public static final String ID = DefaultMod.makeID(IllyaInstallShielder.class.getSimpleName());
    public static final String IMG = makeCardPath("Illya/InstallShielder.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = InstallColorEnum.COLOR_INSTALL;

    private static final int COST = 1;

    public static final IllyaInstallObj INSTALL = new IllyaInstallObj("Shielder");

    public IllyaInstallShielder(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, INSTALL);
        this.defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber = 15;
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
        AbstractDungeon.actionManager.addToTop(new InstallShielderAction(p));
        AbstractDungeon.actionManager.addToTop(new HealAction(p, p, 5));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IllyaShielderPower(p, p, 1, install)));//, 1, AbstractGameAction.AttackEffect.NONE));
    }
}
