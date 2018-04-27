(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('AgentMasterDetailController', AgentMasterDetailController);

    AgentMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AgentMaster'];

    function AgentMasterDetailController($scope, $rootScope, $stateParams, previousState, entity, AgentMaster) {
        var vm = this;

        vm.agentMaster = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testApp:agentMasterUpdate', function(event, result) {
            vm.agentMaster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
