(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('StatusDialogController', StatusDialogController);

    StatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status'];

    function StatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Status) {
        var vm = this;

        vm.status = entity;
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
            if (vm.status.id !== null) {
                Status.update(vm.status, onSaveSuccess, onSaveError);
            } else {
                Status.save(vm.status, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:statusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
