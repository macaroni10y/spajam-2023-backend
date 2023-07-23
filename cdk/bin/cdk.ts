#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { Spajam2023Stack } from '../lib/spajam-2023-stack';

const app = new cdk.App();
new Spajam2023Stack(app, 'Spajam2023Stack');
