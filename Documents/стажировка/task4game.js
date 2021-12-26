const crypto = require('crypto');
const { SHA3 } = require('sha3');
const ignored_argument_amount = 2;
var readline = require('readline-sync');
const win_word = 'win';
const lose_word = 'lose';
const draw_word = 'draw';

function showMessageForStartParameters(text) {
    console.log(text);
    console.log('example of correct input: rock, scissors, paper');
}

function getRandomNonNegativeIntLessThan(max) {  
    return Math.floor(Math.random() * max);
}

class Key {
    key;
    constructor(key_length) {
        this.key = crypto.generateKeySync('hmac', { length: key_length });
    }
    printToConsole() {
        console.log('HMAC key: ' + this.key.export().toString('hex'));
    }
    get() {
        return this.key;
    }
}

class Hmac {
    hmac;
    constructor(algorithm, key) {
        this.hmac = crypto.createHmac(algorithm, key.get());
    }
    update(data) {
        this.hmac.update(data);
    }
    printToConsole() {
        console.log('HMAC chipher: ' + this.hmac.digest('hex'));
    }
}

class Rules {
    moves = [];
    constructor(moves) {
        this.moves = moves;
    }
    showHelpMessage() {
        console.log('the move index should be from 0 included to ' + this.moves.length - 1 + 'included');
    }
    checkIndex(index) {
        if (index < 0 || index >= this.moves.length) {
            console.log('index ' + index + ' is out of bounds');
            return false;
        }
        return true;
    }
    returnGreaterIndexByRule(index1, index2) {
        if (this.checkIndex(index1) && this.checkIndex(index2)) {
            if (index2 - index1 <= (this.moves.length - 1) / 2) return index2;
            else return index1;
        }
        else return;
    }
    returnWinnerIndex(index1, index2) {
        if (index1 == index2) return;
        else {
            if (index2 < index1) [index1, index2] = [index2, index1];
            return this.returnGreaterIndexByRule(index1, index2);
        }
    }
    resultFirstToSecondByWord(move1_index, move2_index) {
        switch (this.returnWinnerIndex(move1_index, move2_index)) {
            case move1_index:
                return win_word; 
            case move2_index:
                return lose_word;
            default:
                if (move1_index == move2_index) return draw_word;
                else this.showHelpMessage();
        }
    }
    print() {
        this.moves.forEach(element => {
            console.log(element);
        });
    }
}

class Helper {
    table;
    constructor(moves) {
        const rules = new Rules(moves);
        this.table = new Array(moves.length);
        for (var i = 0; i <= moves.length; i++) {
            this.table[i] = new Array(moves.length + 1);
        }
        for(var i = 1; i <= moves.length; i++) {
            //this.table[0][0] = 'moves';
            this.table[i][0] = moves[i - 1];
            this.table[0][i] = moves[i - 1];
            for (var j = 1; j <= moves.length; j++) {
                this.table[i][j] = rules.resultFirstToSecondByWord(i - 1, j - 1);
            }
        }
    }
    printToConsole() {
        //console.log(this.table);
        console.table(this.table);
    }
}

class Menu {
    moves;
    table;
    user_move;
    constructor(moves, helper) {
        this.moves = moves;
        this.table = helper;
    }
    printToConsole() {
        console.log('Available moves:');
        for(var i = 1; i < this.moves.length + 1; i++) {
            console.log(i + ' - ' + this.moves[i - 1]);
        }
        console.log('0 - exit');
        console.log('? - help');
    }
    chooseOnUserMove() {
        if (this.user_move == 0) this.processExitRequest();
        else if (this.user_move == '?') this.processHelpRequest();
        else if (this.user_move >= 1 && this.user_move <= this.moves.length) this.processAvailableChoice();
        else this.processNotAvailableChoice();
    }
    processUserChoice() {
        this.user_move = readline.question('Enter your move: ');
        this.chooseOnUserMove();
        return this.user_move;
    }
    processHelpRequest() {
        console.log('Row and column under zero index are headings');
        console.log('From row to column, one move to another has a status noted in the table:');
        this.table.printToConsole();
        this.processUserChoice();
    }
    processAvailableChoice() {
        console.log('Your move: ' + this.moves[this.user_move - 1]);
    }
    processNotAvailableChoice() {
        console.log('You have chosen not available move. Please, change your choice');
        this.printToConsole();
        this.processUserChoice();
    }
    processExitRequest() {
        console.log('You chose to exit the program. Good Bye!');
    }
}

class Game {
    moves;
    menu;
    computer_move;
    secret_key;
    hmac;
    want_exit;
    constructor(moves) {
        this.moves = moves;
        this.menu = new Menu(moves, new Helper(moves));
        this.want_exit = false;
    }
    resetData() {
        this.computer_move =  getRandomNonNegativeIntLessThan(this.moves.length);
        this.secret_key = new Key(512);
        this.hmac = new Hmac('sha256', this.secret_key);
        this.hmac.update(this.moves[this.computer_move]);
    }
    play() {
        this.playRoundAndContinue();
        while(!this.want_exit) {
            console.log('new Round started!');
            this.playRoundAndContinue();
        }
    }
    playRoundAndContinue() {
        this.resetData();
        this.hmac.printToConsole();
        this.menu.printToConsole();
        const user_move = this.menu.processUserChoice();
        if (user_move == 0) this.want_exit = true;
        else {
            console.log('Computer move: ' + this.moves[this.computer_move]);
            this.printRoundResultToConsole(user_move);
            this.secret_key.printToConsole();
        }
    }
    printRoundResultToConsole(user_move) {
        switch((new Rules(this.moves)).resultFirstToSecondByWord(user_move - 1, this.computer_move)) {
            case win_word: console.log('You won!'); return;
            case lose_word: console.log('You lose...'); return;
            case draw_word: console.log('You have a draw'); return;
            default: console.log('Something went wrong, how did you do that?!'); return;
        }
    }
}

if (process.argv.length - ignored_argument_amount <= 1) {
    showMessageForStartParameters('not enought arguments: amount of moves should be greater than 1');
    return;
}
else if (process.argv.length % 2 == 0) {
    showMessageForStartParameters('illegal input: amount of moves should be odd');
    return;
}
else if (process.argv.length - ignored_argument_amount != new Set(process.argv.slice(ignored_argument_amount)).size) {
    showMessageForStartParameters('illegal input: every move should have unique name');
    return;
}

var game = new Game(process.argv.slice(ignored_argument_amount));
game.play();