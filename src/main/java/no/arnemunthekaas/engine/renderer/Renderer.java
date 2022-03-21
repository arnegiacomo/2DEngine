package no.arnemunthekaas.engine.renderer;

import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.SpriteRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer {

    private List<RenderBatch> batches;
    private final int MAX_BATCH_SIZE = 1000; // Amount of sprites that can be added to a batch

    /**
     * Creates new Renderer
     */
    public Renderer() {
        this.batches = new ArrayList<>();
    }


    /**
     * Add GameObject to Renderer batches. If batch is full, creates new batch and adds object to it. Only add sprites of same z-index to same batch
     * @param obj Game Object to batch
     */
    public void add(GameObject obj) {
        SpriteRenderer spr = obj.getComponent(SpriteRenderer.class);
        if(spr != null)
            add(spr);
    }

    private void add(SpriteRenderer spr) {
        boolean added = false;

        for (RenderBatch batch: batches) {
            if(batch.hasSpace() && batch.zIndex() == spr.gameObject.zIndex()) {
                Texture tex = spr.getTexture();
                if(tex == null || (batch.containsTexture(tex) || batch.hasTextureSpace())) {
                    batch.addSprite(spr);
                    added = true;
                    break;
                }
            }
        }

        if(!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, spr.gameObject.zIndex());
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(spr);
            Collections.sort(batches);
        }
    }

    /**
     * Renders all batches
     */
    public void render() {
        for(RenderBatch batch : batches) {
            batch.render();
        }
    }

}