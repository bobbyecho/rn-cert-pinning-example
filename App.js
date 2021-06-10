/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import axios from 'axios';
import { View, Text, Button, Alert, ScrollView } from "react-native";

const App = () => {
  const [text, setText] = React.useState();

  const request = () => {
    const instance = axios.create({
      baseURL: 'https://api.fingerinc.xyz',
    });

    instance
      .get('')
      .then(res => {
        setText(JSON.stringify(res));
      })
      .catch(res => {
        setText(res.message)
        console.log(JSON.stringify(res, null, 4));
      });
  };

  return (
    <ScrollView
      contentContainerStyle={{flex: 1, alignItems: 'center', justifyContent: 'center'}}
    >
      <Text style={{color: 'white'}}>{text}</Text>
      <View style={{paddingTop: 50}}>
        <Button title="fetch me" onPress={request} />
      </View>
    </ScrollView>
  );
};

export default App;
