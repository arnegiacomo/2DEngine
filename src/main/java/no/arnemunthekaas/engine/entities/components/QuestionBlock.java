package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.Prefabs;
import no.arnemunthekaas.engine.entities.components.animation.StateMachine;
import no.arnemunthekaas.utils.AssetPool;
import no.arnemunthekaas.utils.GameConstants;

public class QuestionBlock extends Block {
    // https://youtu.be/NNioIsbxszU?t=2008
    private enum BlockType {
        Coin,
        Powerup,
        Invincibility
    }

    public BlockType blockType = BlockType.Coin;

    @Override
    void playerHit(PlayerController playerController) {
        switch(blockType) {
            case Coin:
                doCoin(playerController);
                break;
            case Powerup:
                doPowerup(playerController);
                break;
            case Invincibility:
                doInvincibility(playerController);
                break;
        }

        StateMachine stateMachine = gameObject.getComponent(StateMachine.class);
        if (stateMachine != null) {
            stateMachine.trigger("setInactive");
            this.setInactive();
        }
    }

    private void doInvincibility(PlayerController playerController) {
    }

    private void doPowerup(PlayerController playerController) {
    }

    private void doCoin(PlayerController playerController) {
        GameObject coin = Prefabs.generateBlockCoin();
        coin.transform.position.set(this.gameObject.transform.position);
        coin.transform.position.y += GameConstants.GRID_HEIGHT;
        Window.getScene().addGameObject(coin);
    }
}
