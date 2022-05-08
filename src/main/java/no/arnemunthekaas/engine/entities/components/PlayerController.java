package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.animation.StateMachine;
import no.arnemunthekaas.engine.entities.components.physics2d.components.PillboxCollider;
import no.arnemunthekaas.engine.entities.components.physics2d.components.RaycastInfo;
import no.arnemunthekaas.engine.entities.components.physics2d.components.Rigidbody2D;
import no.arnemunthekaas.engine.events.listeners.KeyListener;
import no.arnemunthekaas.utils.AssetPool;
import no.arnemunthekaas.utils.GameConstants;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;


public class PlayerController extends Component {

    private enum PlayerState {
        Small,
        Big,
        Fire,
        Invincible
    }


    public float walkSpeed = 1.9f;
    public float jumpBoost = 1.0f;
    public float jumpImpulse = 3.0f;
    public float slowDownForce = 0.05f;
    public Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);
    private PlayerState playerState = PlayerState.Small;

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


        checkOnGround();
        if(KeyListener.isKeyPressed(GLFW_KEY_SPACE) && (jumpTime > 0 || onGround || groundDebounce > 0)) {
            if ((onGround || groundDebounce > 0) && jumpTime == 0) {
                AssetPool.getSound("assets/audio/assets_sounds_jump-small.ogg").play();
                jumpTime = 64;
                this.velocity.y = jumpImpulse;
            } else if (jumpTime > 0) {
                jumpTime--;
                this.velocity.y = (jumpTime /2.2f) * jumpBoost;
            } else {
                this.velocity.y = 0;
            }
            groundDebounce = 0;
        } else if (!onGround) {
            if (this.jumpTime > 0) {
                this.velocity.y *= 0.35f;
                this.jumpTime = 0;
            }
            groundDebounce -= dt;
            this.acceleration.y = Window.getPhysics().getGravity().y;
        } else {
            this.velocity.y = 0;
            this.acceleration.y = 0;
            groundDebounce = groundDebounceTime;
        }


        this.acceleration.y = Window.getPhysics().getGravity().y;

        this.velocity.x += this.acceleration.x * dt;
        this.velocity.y += this.acceleration.y * dt;
        this.velocity.x = Math.max(Math.min(this.velocity.x, this.terminalVelocity.x), -this.terminalVelocity.x);
        this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -this.terminalVelocity.y);

        this.rigidbody2D.setVelocity(this.velocity);
        this.rigidbody2D.setAngularVelocity(0);

        if (!onGround)
            stateMachine.trigger("jump");
        else
            stateMachine.trigger("stopJumping");
    }

    /**
     * TODO: Change this when creating own game?
     */
    public void checkOnGround() {
        Vector2f raycastBegin = new Vector2f(this.gameObject.transform.position);
        float innerPlayerWidth = this.playerWidth * 0.6f;
        raycastBegin.sub(innerPlayerWidth / 2.0f, 0.0f);
        float yVal = playerState == PlayerState.Small ? -0.16f : -0.26f;
        Vector2f raycastEnd = new Vector2f(raycastBegin).add(0.0f, yVal);

        RaycastInfo info = Window.getPhysics().raycast(gameObject, raycastBegin, raycastEnd);

        Vector2f raycast2Begin = new Vector2f(raycastBegin).add(innerPlayerWidth, 0.0f);
        Vector2f raycast2End = new Vector2f(raycastEnd).add(innerPlayerWidth, 0.0f);
        RaycastInfo info2 = Window.getPhysics().raycast(gameObject, raycast2Begin, raycast2End);

        onGround = (info.hit && info.hitObject != null && info.hitObject.getComponent(Ground.class) != null) ||
                (info2.hit && info2.hitObject != null && info2.hitObject.getComponent(Ground.class) != null);


//        DebugDraw.addLine2D(raycastBegin, raycastEnd, new Vector3f(1, 0, 0));
//        DebugDraw.addLine2D(raycast2Begin, raycast2End, new Vector3f(1, 0, 0));
    }

    @Override
    public void beginContact(GameObject collidingObject, Contact contact, Vector2f contactNormal) {
        if (isDead) return;

        if (collidingObject.getComponent(Ground.class) != null) {
            if (Math.abs(contactNormal.x) > 0.8f) {
                this.velocity.x = 0;
            } else if (contactNormal.y > 0.8f) {
                this.velocity.y = 0;
                this.acceleration.y = 0;
                this.jumpTime = 0;
            }
        }
    }

    /**
     *
     * @return
     */
    public boolean isSmall() {
        return this.playerState == PlayerState.Small;
    }

    /**
     *
     * @return
     */
    public boolean isBig() {
        return this.playerState == PlayerState.Big;
    }

    /**
     *
     * @param consumable
     */
    public void consume(Consumable consumable) {
        if (consumable instanceof Potion) {

                if (playerState == PlayerState.Small) {
                    playerState = PlayerState.Big;
                    AssetPool.getSound("assets/audio/mario1up.ogg").play();
                    gameObject.transform.scale.y = 0.42f;
                    PillboxCollider pillboxCollider = gameObject.getComponent(PillboxCollider.class);

                    if(pillboxCollider != null) {
                        jumpBoost *= bigJumpBoostFactor;
                        walkSpeed *= bigJumpBoostFactor;
                        pillboxCollider.setHeight(0.63f);
                    }
                } else if (playerState == PlayerState.Big) {
                    AssetPool.getSound("assets/audio/mario1up.ogg").play();
                    // TODO: inventory system
                }

                stateMachine.trigger("powerup");




        }
    }

}
