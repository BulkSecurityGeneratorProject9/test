(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('AgentMasterDeleteController',AgentMasterDeleteController);

    AgentMasterDeleteController.$inject = ['$uibModalInstance', 'entity', 'AgentMaster'];

    function AgentMasterDeleteController($uibModalInstance, entity, AgentMaster) {
        var vm = this;

        vm.agentMaster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AgentMaster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
