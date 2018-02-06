(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DrugAddictionDialogController', DrugAddictionDialogController);

    DrugAddictionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DrugAddiction'];

    function DrugAddictionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DrugAddiction) {
        var vm = this;

        vm.drugAddiction = entity;
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
            if (vm.drugAddiction.id !== null) {
                DrugAddiction.update(vm.drugAddiction, onSaveSuccess, onSaveError);
            } else {
                DrugAddiction.save(vm.drugAddiction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:drugAddictionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
