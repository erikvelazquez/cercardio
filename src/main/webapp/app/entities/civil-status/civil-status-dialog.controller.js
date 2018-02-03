(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('CivilStatusDialogController', CivilStatusDialogController);

    CivilStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CivilStatus'];

    function CivilStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CivilStatus) {
        var vm = this;

        vm.civilStatus = entity;
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
            if (vm.civilStatus.id !== null) {
                CivilStatus.update(vm.civilStatus, onSaveSuccess, onSaveError);
            } else {
                CivilStatus.save(vm.civilStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:civilStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
