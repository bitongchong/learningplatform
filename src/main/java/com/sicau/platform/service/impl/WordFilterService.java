package com.sicau.platform.service.impl;

import org.apache.commons.lang3.CharUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author boot liu
 */
@Service
public class WordFilterService implements InitializingBean {
    private TrieNode rootNode = new TrieNode();

	@Override
    public void afterPropertiesSet() {
        try {
            InputStream iStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("FilterWords.txt");
            if (Objects.isNull(iStream)) {
            	throw new Exception("获取失败");
			}
            InputStreamReader reader = new InputStreamReader(iStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String word;
            while ((word = bufferedReader.readLine()) != null) {
                addWord(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private static class TrieNode {
        private boolean end = false;
        HashMap<Character, TrieNode> nextNodes = new HashMap<>();

        public void setStatus(boolean end) {
            this.end = end;
        }

        TrieNode getNextNodes(Character key) {
            return nextNodes.get(key);
        }

        boolean isEnd() {
            return this.end;
        }

        void addNode(Character key, TrieNode nextNodes) {
            this.nextNodes.put(key, nextNodes);
        }
    }

    private boolean isSpecialSymbol(char c) {
        // 东亚文字的范围0x2E80-0x9FFF，可以去除大部分特殊字符以及跳过所有的空格
        return !CharUtils.isAsciiAlphanumeric(c) && ((int) c < 0x2E80 || (int) c > 0x9FFF);
    }

	/**
	 *  添加节点
	 * @param word -
	 */
	private void addWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        Character character;
        TrieNode temp = rootNode;
        for (int i = 0; i < word.length(); i++) {
            character = word.charAt(i);
            if (temp.getNextNodes(character) == null) {
                temp.addNode(character, new TrieNode());
                temp = temp.getNextNodes(character);
            } else {
                temp = temp.getNextNodes(character);
            }
            if (i == word.length() - 1) {
                temp.setStatus(true);
            }
        }
    }

	/**
	 * 过滤文本
	 * @param text -
	 * @return -
	 */
	String filter(String text) {
        if (text.isEmpty()) {
            return text;
        }
        StringBuilder sb = new StringBuilder();
        TrieNode temp = rootNode;
        int start = 0;
        int end = 0;
        char word;
        while (end < text.length()) {
            word = text.charAt(end);
            // 如果是特殊字符就跳过，同时end指针向后移
            if (isSpecialSymbol(word)) {
                end++;
                continue;
            }
            temp = temp.getNextNodes(word);
            if (temp == null) {
                sb.append(text.charAt(start));
                start++;
                end = start;
                temp = rootNode;
            } else if (temp.isEnd()) {
                end++;
                start = end;
				String replaceWord = "***";
				sb.append(replaceWord);
                temp = rootNode;
            } else {
                end++;
            }
            // 避免既没有到达end条件，又一直匹配着屏蔽词到text的最后。比如屏蔽词中有“test a”，在匹配“test”的时候会直接被吞掉。
            if (end == text.length()) {
                for (int j = start; j < end; j++) {
                    sb.append(text.charAt(j));
                }
            }
        }
        return sb.toString();
    }

//	public static void main(String[] args) {
//		WordFilterService wordFilterService = new WordFilterService();
//		try {
//			InputStream iStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("FilterWords.txt");
//			InputStreamReader reader = new InputStreamReader(iStream);
//			BufferedReader bufferedReader = new BufferedReader(reader);
//			String word;
//			while ((word = bufferedReader.readLine()) != null) {
//				wordFilterService.addWord(word);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(wordFilterService.filter("test"));
//	}
}
