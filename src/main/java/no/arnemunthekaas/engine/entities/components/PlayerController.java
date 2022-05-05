package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.entities.components.animation.StateMachine;
import no.arnemunthekaas.engine.entities.physics2d.components.Rigidbody2D;
import no.arnemunthekaas.engine.eventlisteners.KeyListener;
import no.arnemunthekaas.utils.GameConstants;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;


public class PlayerController extends Component {

    public float walkSpeed = 1.9f;
    public float jumpBoost = 1.0f;
    public float jumpImpulse = 3.0f;
    public float slowDownForce = 0.05f;
    public Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);

    public transient boolean onGround;
    private transient float groundDebounce = 0.0f;
    private transient float groundDebounceTime = 0.1f;
    private transient Rigidbody2D rigidbody2D;
    private transient StateMachine stateMachine;
    private transient float bigJumpBoostFactor = 1.05f;
    private transient float playerWidth = GameConstants.GRID_WIDTH;
    private transient int jumpTime = 0;
    private transient Vector2f acceleration = new Vector2f();
    private transient Vector2f velocity = new Vector2f();
    private transient boolean isDead;
    private transient int enemyBounce = 0;

    @Override
    public void start() {
        this.rigidbody2D = gameObject.getComponent(Rigidbody2D.class);
        this.stateMachine = gameObject.getComponent(StateMachine.class);
        this.rigidbody2D.setGravityScale(0.0f);
    }

    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || KeyListener.isKeyPressed(GLFW_KEY_D)) {
            this.gameObject.transform.scale.x = playerWidth;
            this.acceleration.x = walkSpeed;

            if (this.velocity.x < 0) {
                this.stateMachine.trigger("switchDirection");
                this.velocity.x += slowDownForce;
            } else {
                this.stateMachine.trigger("startRunning");
            }
    // TODO : Add keybinds
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT) || KeyListener.isKeyPressed(GLFW_KEY_A)) {
            this.gameObject.transform.scale.x = - playerWidth;
            this.acceleration.x = - walkSpeed;

            if (this.velocity.x > 0) {
                this.stateMachine.trigger("switchDirection");
                this.velocity.x -= slowDownForce;
            } else {
                this.stateMachine.trigger("startRunning");
            }

        } else {
            this.acceleration.x = 0;
            if (this.velocity.x > 0)
                this.velocity.x = Math.max(0, this.velocity.x - slowDownForce);
            else if (this.velocity.x < 0)
                this.velocity.x = Math.min(0, this.velocity.x + slowDownForce);
        }

        if(this.velocity.x == 0)
            this.stateMachine.trigger("stopRunning");

        this.acceleration.y = Window.getPhysics().getGravity().y;

        this.velocity.x += this.acceleration.x * dt;
        this.velocity.y += this.acceleration.y * dt;
        this.velocity.x = Math.max(Math.min(this.velocity.x, this.terminalVelocity.x), -this.terminalVelocity.x);
        this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -this.terminalVelocity.y);

        this.rigidbody2D.setVelocity(this.velocity);
        this.rigidbody2D.setAngularVelocity(0);
    }

}
