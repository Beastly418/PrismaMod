package PrismaMod.actions.CardActions.Illya.Install;

import com.megacrit.cardcrawl.characters.AbstractPlayer;


public class InstallAlterEgoAction extends AbstractInstallAction {

    public InstallAlterEgoAction(AbstractPlayer p){
        this.p = p;
        this.handler = getHandler(p);
        //AbstractDungeon.effectsQueue.add(new InstallBaseSFX(p));
    }

    @Override
    public void update() {

        this.tickDuration();
    }
}
