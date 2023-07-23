import * as cdk from 'aws-cdk-lib';
import {RemovalPolicy} from 'aws-cdk-lib';
import {Construct} from 'constructs';
import {Repository} from "aws-cdk-lib/aws-ecr";
import {AttributeType, Table} from "aws-cdk-lib/aws-dynamodb";
import {Service, Source} from '@aws-cdk/aws-apprunner-alpha';
import {Role, ServicePrincipal} from "aws-cdk-lib/aws-iam";

export class Spajam2023Stack extends cdk.Stack {
    constructor(scope: Construct, id: string, props?: cdk.StackProps) {
        super(scope, id, props);

        const repository = new Repository(this, 'Repository', {
            repositoryName: 'spajam-2023-api',
            removalPolicy: RemovalPolicy.DESTROY,
        });

        const appRunnerInstanceRole = new Role(this, 'InstanceRole', {
            assumedBy: new ServicePrincipal('tasks.apprunner.amazonaws.com'),
        });
        repository.grantPull(appRunnerInstanceRole)

        new Service(this, 'Service', {
            autoDeploymentsEnabled: true,
            instanceRole: appRunnerInstanceRole,
            source: Source.fromEcr({
                repository: repository,
            }),
        });

        new Table(this, 'DynamoDb', {
            tableName: 'Conversation',
            partitionKey: {
                name: 'conversationId',
                type: AttributeType.STRING,
            },
            removalPolicy: RemovalPolicy.DESTROY,
        }).grantFullAccess(appRunnerInstanceRole);
    }
}
