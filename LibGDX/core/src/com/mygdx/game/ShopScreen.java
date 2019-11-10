package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

/**
 * This class is part of an updated and not working version of the game so for this hack it is not implemented.
 * @author Ali
 *
 */
public class ShopScreen implements Screen {
	
	MainScreen screen;
	Table t = new Table();
	private float scaleItem;
	private Boolean isClicked;
	private int XArray = -1;
	private int YArray = -1;
	
	private Image[][] item;

	
	public ShopScreen(MainScreen screen1) {
		
		this.screen = screen1;
		this.t = new Table();
		this.t.setFillParent(true);
		
		float width = Gdx.graphics.getWidth();
		scaleItem = width/1920;
		
		final Image Title = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/SHOP.png")))));
		Title.setScaling(Scaling.fit);
		Title.setPosition(50f*scaleItem, Gdx.graphics.getHeight()- Title.getHeight()*scaleItem - 50f*scaleItem);
		Title.setSize(Title.getWidth()*scaleItem, Title.getHeight()*scaleItem);
		t.addActor(Title);
		
		isClicked = false;
		item = new Image[5][5];

		createItemSlots();

		final Image Leave = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/LEAVE.png")))));
		Leave.setScaling(Scaling.fit);
		Leave.setPosition(Gdx.graphics.getWidth()-40f*scaleItem-Leave.getWidth()*scaleItem,40f*scaleItem);
		Leave.setSize(Leave.getWidth()*scaleItem, Leave.getHeight()*scaleItem);
		Leave.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
				dispose();
				screen.setScreen(new HouseScreen(screen));
		    }
		    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	    		TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/LEAVEMOUSE.png"))));
	    		Leave.setDrawable(t);
		    }
		    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	    		TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/LEAVE.png"))));
	    		Leave.setDrawable(t);
		    }
		});
		t.addActor(Leave);
		

		final Image Buy = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/BUY.png")))));
		Buy.setScaling(Scaling.fit);
		Buy.setPosition(Gdx.graphics.getWidth()-40f*scaleItem-Buy.getWidth()*scaleItem,40f*2f*scaleItem + Leave.getHeight());
		Buy.setSize(Buy.getWidth()*scaleItem, Buy.getHeight()*scaleItem);
		Buy.addListener(new ClickListener(){
			@Override
		    public void clicked(InputEvent event, float x, float y) {
		    }
		    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	    		TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/BUYMOUSE.png"))));
	    		Buy.setDrawable(t);
		    }
		    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	    		TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/BUY.png"))));
	    		Buy.setDrawable(t);
		    }
		});
		t.addActor(Buy);

		Gdx.input.setInputProcessor(screen.ui);

		
		screen.ui.addActor(t);
	}
	
	public void createItemSlots() {
		for(int y = 0; y < 5; y++) {
			for(int x = 0; x < 5; x++) {
				final int X = x;
				final int Y = y;
				
				
				final Image ItemSlot1 = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/ITEM.png")))));
				ItemSlot1.setScaling(Scaling.fit);
				ItemSlot1.setPosition(50f*scaleItem + ItemSlot1.getWidth()*x*scaleItem, Gdx.graphics.getHeight() - ItemSlot1.getHeight()*scaleItem - 200f*scaleItem - ItemSlot1.getHeight()*y*scaleItem);
				ItemSlot1.setSize(ItemSlot1.getWidth()*scaleItem, ItemSlot1.getHeight()*scaleItem);
				
				ItemSlot1.addListener(new ClickListener(){
					@Override
				    public void clicked(InputEvent event, float x, float y) {
			    		TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/ITEMHOVER.png"))));
			    		if(isClicked) {
				    		TextureRegionDrawable t2 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/ITEM.png"))));
			    			item[YArray][XArray].setDrawable(t2);
			    			YArray = Y;
			    			XArray = X;
			    		}
			    		else {
				    		isClicked = true;
			    			YArray = Y;
			    			XArray = X;
			    		}
			    		ItemSlot1.setDrawable(t);
				    }
				    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
			    		TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/ITEMHOVER.png"))));
			    		ItemSlot1.setDrawable(t);
				    }
				    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				    	if(!(X == XArray) || !(Y == YArray)) {
				    		TextureRegionDrawable t = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("shop/ITEM.png"))));
			    			ItemSlot1.setDrawable(t);
				    	}
				    }
				});
				item[Y][X] = ItemSlot1;
				t.addActor(ItemSlot1);
			}
		}
	}
	
		
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		screen.ui.act(delta);
		screen.ui.draw();		
	}

	@Override
	public void resize(int width, int height) {
		screen.ui.getViewport().update(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
