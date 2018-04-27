(function() {
    'use strict';

    angular
        .module('testApp')
        .controller('AgentMasterDialogController', AgentMasterDialogController);

    AgentMasterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AgentMaster'];

    function AgentMasterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AgentMaster) {
        var vm = this;

        vm.agentMaster = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.agentMaster.id !== null) {
                AgentMaster.update(vm.agentMaster, onSaveSuccess, onSaveError);
            } else {
                AgentMaster.save(vm.agentMaster, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testApp:agentMasterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
