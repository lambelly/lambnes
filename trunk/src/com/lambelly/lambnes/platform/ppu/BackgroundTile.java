package com.lambelly.lambnes.platform.ppu;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.*;

import com.lambelly.lambnes.platform.Platform;
import com.lambelly.lambnes.util.BitUtils;
import com.lambelly.lambnes.util.NumberConversionUtils; 

public class BackgroundTile extends NesTile
{
	
	public BackgroundTile()
	{
		
	}
	
	public BackgroundTile(int backgroundNumber, int colorHighBit)
	{
		super(backgroundNumber, colorHighBit);
	}
	
	public BackgroundTile(int backgroundNumber)
	{
		super(backgroundNumber);
	}
	
	public BackgroundTile(BackgroundTile background)
	{
		super(background);
	}
	
	@Override
	protected void instantiateTile()
	{
		//if(logger.isDebugEnabled())
		{
			//logger.debug("instantiating background: " + this.getTileNumber());
		}
		int[] background = this.getTileByteArray(this.getTileNumber());
		this.setPatternA(ArrayUtils.subarray(background, 0, 8));
		if(logger.isDebugEnabled())
		{
			logger.debug(this.getPatternA().length);
		}
		this.setPatternB(ArrayUtils.subarray(background, 8, 16));
		if(logger.isDebugEnabled())
		{
			logger.debug(this.getPatternB().length);
		}
	}
	
	public int getPixelBackgroundColorPaletteIndex(int column, int row)
	{
		int patternABit = (BitUtils.isBitSet(this.getPatternA()[row],column))?1:0;
		int patternBBit = (BitUtils.isBitSet(this.getPatternB()[row],column))?1:0;
	
		if(logger.isDebugEnabled())
		{
			logger.debug("generating pixel color for " + column + ", " + row);
			logger.debug(this.toString());
			logger.debug("pattern a row " + row + ": " + Integer.toBinaryString(this.getPatternA()[row]));
			logger.debug("pattern b row " + row + ": " + Integer.toBinaryString(this.getPatternB()[row]));
			logger.debug("pattern a pixel bit: " + patternABit);
			logger.debug("pattern b pixel bit: " + patternBBit);
			logger.debug("highbit: " + this.getBackgroundAttributes().getColorHighBit());
		}
		
		int lowbit = (patternBBit) << 1 | (patternABit); 
		int color = (this.getBackgroundAttributes().getColorHighBit() << 2) | lowbit;
		if(logger.isDebugEnabled())
		{
			logger.debug("color bitstring generated for " + column + ", " + row + ": " + Integer.toBinaryString(color));
		}
		return color;
	}
	
	@Override
	public int getHighBit()
	{
		return Platform.getPpu().getPpuControlRegister().getBackgroundPatternTableAddress();
	}
	
	public String toString()
	{
		// generate color map string
		String colorMapString = "";
		for (int row = 0; row < 8; row++)
		{
			colorMapString+= "\n";
			for (int col = 0; col < 8; col++)
			{
				colorMapString += this.getPixelBackgroundColorPaletteIndex(col, row);
			}
		}
		
		return "background tile number: " + this.getTileNumber() + "\n" + 
			"patternA: \n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternA[0], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternA[1], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternA[2], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternA[3], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternA[4], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternA[5], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternA[6], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternA[7], 8) + "\n" +
			"patternB: \n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternB[0], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternB[1], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternB[2], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternB[3], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternB[4], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternB[5], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternB[6], 8) + "\n" +
			"\t" + NumberConversionUtils.generateBinaryStringWithleadingZeros(this.patternB[7], 8) + "\n" +
			"\t\tcolor high bit: " + this.getBackgroundAttributes().getColorHighBit() + "\n" +
			"\t\tbackground color palette index map: " + colorMapString;
	}
}
