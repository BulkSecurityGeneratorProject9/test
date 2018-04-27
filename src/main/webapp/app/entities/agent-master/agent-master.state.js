(function() {
    'use strict';

    angular
        .module('testApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('agent-master', {
            parent: 'entity',
            url: '/agent-master',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AgentMasters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agent-master/agent-masters.html',
                    controller: 'AgentMasterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('agent-master-detail', {
            parent: 'agent-master',
            url: '/agent-master/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AgentMaster'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agent-master/agent-master-detail.html',
                    controller: 'AgentMasterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AgentMaster', function($stateParams, AgentMaster) {
                    return AgentMaster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'agent-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('agent-master-detail.edit', {
            parent: 'agent-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent-master/agent-master-dialog.html',
                    controller: 'AgentMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AgentMaster', function(AgentMaster) {
                            return AgentMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agent-master.new', {
            parent: 'agent-master',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent-master/agent-master-dialog.html',
                    controller: 'AgentMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                agentNumber: null,
                                agentName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('agent-master', null, { reload: 'agent-master' });
                }, function() {
                    $state.go('agent-master');
                });
            }]
        })
        .state('agent-master.edit', {
            parent: 'agent-master',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent-master/agent-master-dialog.html',
                    controller: 'AgentMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AgentMaster', function(AgentMaster) {
                            return AgentMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agent-master', null, { reload: 'agent-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agent-master.delete', {
            parent: 'agent-master',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent-master/agent-master-delete-dialog.html',
                    controller: 'AgentMasterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AgentMaster', function(AgentMaster) {
                            return AgentMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agent-master', null, { reload: 'agent-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
