	
package com.old.silence.content.infrastructure.cache;

public class LuaScriptUtils {

	private static String MULTI_STOCK_LUA;

	private static String MULTI_STOCK_LUA_ADD;

	public static String getMultiStockDeductionLua() {
		if (MULTI_STOCK_LUA == null) {
			StringBuilder sb = new StringBuilder();
			// 1. 记录库存不足的情况
			sb.append("  local myTable = {} ");
			// 2. 判断目前的库存量是否满足需求量
			sb.append(" local redisQtys = redis.call('mget',unpack(KEYS)) ");
			sb.append(" for i = 1, #KEYS  do ");
			sb.append("    if tonumber(ARGV[i]) > tonumber(redisQtys[i])  then ");
			sb.append("        myTable[#myTable + 1] = KEYS[i] .. '=' .. redisQtys[i]  ");
			sb.append("     end ");
			sb.append(" end ");
			// 3. myTable 有元素  return  ,没有元素 就扣
			sb.append(" if #myTable > 0 then  ");
			sb.append("  return myTable ");
			sb.append(" end ");
			sb.append(" for i = 1 , #KEYS do");
			sb.append("  redis.call('decrby',KEYS[i],ARGV[i]) ");
			sb.append(" end ");
			sb.append(" return {} ");
			// 如果任意一个key扣减不成功则集合非空;如果都满足则集合为空
			MULTI_STOCK_LUA = sb.toString();
		}
		return MULTI_STOCK_LUA;
	}

	public static String getMultiStockAddLua() {
		if (MULTI_STOCK_LUA_ADD == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(" local exists = {} ");
			sb.append(" for i, key in ipairs(KEYS) do ");
			sb.append("    exists[i] = redis.call('exists', key) ");
			sb.append(" end ");
			sb.append(" for i, key in ipairs(KEYS) do");
			sb.append(" 	  redis.call('incrby', key, ARGV[i])");
			sb.append(" end ");
			sb.append(" return {} ");
			// 如果都满足则集合为空
			MULTI_STOCK_LUA_ADD = sb.toString();
		}
		return MULTI_STOCK_LUA_ADD;
	}
}