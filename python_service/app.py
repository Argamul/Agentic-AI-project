
from flask import Flask, request, jsonify
from langchain_groq import ChatGroq
import os
from dotenv import load_dotenv

load_dotenv()

app = Flask(__name__)


llm = ChatGroq(model_name="llama3-8b-8192")

@app.route('/chat', methods=['POST'])
def chat():
    
    data = request.get_json()
    if not data or 'message' not in data:
        return jsonify({'error': 'No message provided'}), 400

    message = data['message']
    
    try:
        
        response = llm.invoke(message)
        
        
        content = response.content
        
        return jsonify({'response': content})
    except Exception as e:
        print(f"An error occurred: {e}")
        return jsonify({'error': 'Failed to get response from LangChain model'}), 500

if __name__ == '__main__':
    
    app.run(host='0.0.0.0', port=5000, debug=True)
