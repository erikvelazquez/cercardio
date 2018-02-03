(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('EthnicGroupDialogController', EthnicGroupDialogController);

    EthnicGroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EthnicGroup'];

    function EthnicGroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EthnicGroup) {
        var vm = this;

        vm.ethnicGroup = entity;
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
            if (vm.ethnicGroup.id !== null) {
                EthnicGroup.update(vm.ethnicGroup, onSaveSuccess, onSaveError);
            } else {
                EthnicGroup.save(vm.ethnicGroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:ethnicGroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
