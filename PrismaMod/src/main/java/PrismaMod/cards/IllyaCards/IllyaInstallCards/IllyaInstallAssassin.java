package PrismaMod.cards.IllyaCards.IllyaInstallCards;


import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Install.InstallAssassinAction;
import PrismaMod.characters.Illya;
import PrismaMod.orbs.IllyaAssassinOrb;
import PrismaMod.patches.InstallColorEnum;
import PrismaMod.powers.Illya.IllyaAssassinPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaInstallAssassin extends AbstractInstallCard {

    public static final String ID = DefaultMod.makeID(IllyaInstallAssassin.class.getSimpleName());
    public static final String IMG = makeCardPath("Illya/InstallAssassin.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = InstallColorEnum.COLOR_INSTALL;

    private static final int COST = 1;

    public static final IllyaInstallObj INSTALL = new IllyaInstallObj("Assassin");

    public IllyaInstallAssassin(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, INSTALL);
        this.baseMagicNumber = this.magicNumber = 75;
        this.defaultBaseSecondMagicNumber = this.defaultSecondMagicNumber = 75;

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
        install.makeAssassin();
        if(p instanceof Illya && !enablePlaceholder)
        AbstractDungeon.actionManager.addToTop(new InstallAssassinAction(p));
        AbstractDungeon.actionManager.addToTop(new ChannelAction(new IllyaAssassinOrb(), true));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IllyaAssassinPower(p, p, 1, install)));//, 1, AbstractGameAction.AttackEffect.NONE));
    }
}

