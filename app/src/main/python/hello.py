import pandas as pd
import numpy as np

import os

from com.chaquo.python import Python

import nltk
from nltk.sentiment import SentimentIntensityAnalyzer
from tqdm.notebook import tqdm

download_dir = "{}/nltk_data".format(Python.getPlatform().getApplication().getFilesDir())
if not os.path.exists(download_dir):
    os.mkdir(download_dir)
nltk.download('vader_lexicon', download_dir=download_dir)

# # using Roberta pretrained model
# from transformers import AutoTokenizer
# from transformers import AutoModelForSequenceClassification
# from scipy.special import softmax



def main(text):

    sia = SentimentIntensityAnalyzer();
    score = sia.polarity_scores(text);





    # MODEL = f"cardiffnlp/twitter-roberta-base-sentiment"
    # tokenizer = AutoTokenizer.from_pretrained(MODEL)
    # model = AutoModelForSequenceClassification.from_pretrained(MODEL)
    #
    # example = "I like sentiment analyzer"
    #
    #
    # # Run for Roberta model
    # encoded_text = tokenizer(example, return_tensors='pt')
    # output = model(**encoded_text)
    # score = softmax(output[0][0].detach().numpy())

    return score

if __name__ == "__main__":
    print(main())